import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IClientUpdateState {
  isNew: boolean;
}

export class ClientUpdate extends React.Component<IClientUpdateProps, IClientUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { clientEntity } = this.props;
      const entity = {
        ...clientEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/client');
  };

  render() {
    const { clientEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.client.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.client.home.createOrEditLabel">Create or edit a Client</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : clientEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="client-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="client-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="client-title">
                    <Translate contentKey="accounTalkApp.client.title">Title</Translate>
                  </Label>
                  <AvField
                    id="client-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                  <UncontrolledTooltip target="titleLabel">
                    <Translate contentKey="accounTalkApp.client.help.title" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="forenameLabel" for="client-forename">
                    <Translate contentKey="accounTalkApp.client.forename">Forename</Translate>
                  </Label>
                  <AvField
                    id="client-forename"
                    type="text"
                    name="forename"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="surnameLabel" for="client-surname">
                    <Translate contentKey="accounTalkApp.client.surname">Surname</Translate>
                  </Label>
                  <AvField
                    id="client-surname"
                    type="text"
                    name="surname"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateOfBirthLabel" for="client-dateOfBirth">
                    <Translate contentKey="accounTalkApp.client.dateOfBirth">Date Of Birth</Translate>
                  </Label>
                  <AvField
                    id="client-dateOfBirth"
                    type="text"
                    name="dateOfBirth"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneNumberLabel" for="client-phoneNumber">
                    <Translate contentKey="accounTalkApp.client.phoneNumber">Phone Number</Translate>
                  </Label>
                  <AvField id="client-phoneNumber" type="text" name="phoneNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="client-email">
                    <Translate contentKey="accounTalkApp.client.email">Email</Translate>
                  </Label>
                  <AvField
                    id="client-email"
                    type="text"
                    name="email"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="selfAssesmentUtrNoLabel" for="client-selfAssesmentUtrNo">
                    <Translate contentKey="accounTalkApp.client.selfAssesmentUtrNo">Self Assesment Utr No</Translate>
                  </Label>
                  <AvField
                    id="client-selfAssesmentUtrNo"
                    type="text"
                    name="selfAssesmentUtrNo"
                    validate={{
                      minLength: { value: 10, errorMessage: translate('entity.validation.minlength', { min: 10 }) },
                      maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nationalityLabel" for="client-nationality">
                    <Translate contentKey="accounTalkApp.client.nationality">Nationality</Translate>
                  </Label>
                  <AvField id="client-nationality" type="text" name="nationality" />
                </AvGroup>
                <AvGroup>
                  <Label id="genderLabel" for="client-gender">
                    <Translate contentKey="accounTalkApp.client.gender">Gender</Translate>
                  </Label>
                  <AvInput
                    id="client-gender"
                    type="select"
                    className="form-control"
                    name="gender"
                    value={(!isNew && clientEntity.gender) || 'MALE'}
                  >
                    <option value="MALE">{translate('accounTalkApp.Gender.MALE')}</option>
                    <option value="FEMALE">{translate('accounTalkApp.Gender.FEMALE')}</option>
                    <option value="OTHER">{translate('accounTalkApp.Gender.OTHER')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="studentLoanLabel" check>
                    <AvInput id="client-studentLoan" type="checkbox" className="form-control" name="studentLoan" />
                    <Translate contentKey="accounTalkApp.client.studentLoan">Student Loan</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="childBenefitLabel" check>
                    <AvInput id="client-childBenefit" type="checkbox" className="form-control" name="childBenefit" />
                    <Translate contentKey="accounTalkApp.client.childBenefit">Child Benefit</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="spouseLabel" check>
                    <AvInput id="client-spouse" type="checkbox" className="form-control" name="spouse" />
                    <Translate contentKey="accounTalkApp.client.spouse">Spouse</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="findOutAboutUsLabel" for="client-findOutAboutUs">
                    <Translate contentKey="accounTalkApp.client.findOutAboutUs">Find Out About Us</Translate>
                  </Label>
                  <AvField id="client-findOutAboutUs" type="text" name="findOutAboutUs" />
                </AvGroup>
                <AvGroup>
                  <Label id="additionalInformationLabel" for="client-additionalInformation">
                    <Translate contentKey="accounTalkApp.client.additionalInformation">Additional Information</Translate>
                  </Label>
                  <AvField id="client-additionalInformation" type="text" name="additionalInformation" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/client" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  clientEntity: storeState.client.entity,
  loading: storeState.client.loading,
  updating: storeState.client.updating,
  updateSuccess: storeState.client.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientUpdate);
