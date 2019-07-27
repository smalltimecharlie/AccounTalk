import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmploymentDetails } from 'app/shared/model/employment-details.model';
import { getEntities as getEmploymentDetails } from 'app/entities/employment-details/employment-details.reducer';
import { getEntity, updateEntity, createEntity, reset } from './benefits.reducer';
import { IBenefits } from 'app/shared/model/benefits.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBenefitsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBenefitsUpdateState {
  isNew: boolean;
  employmentDetailsId: string;
}

export class BenefitsUpdate extends React.Component<IBenefitsUpdateProps, IBenefitsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      employmentDetailsId: '0',
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

    this.props.getEmploymentDetails();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { benefitsEntity } = this.props;
      const entity = {
        ...benefitsEntity,
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
    this.props.history.push('/entity/benefits');
  };

  render() {
    const { benefitsEntity, employmentDetails, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.benefits.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.benefits.home.createOrEditLabel">Create or edit a Benefits</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : benefitsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="benefits-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="benefits-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="benefitTypeLabel" for="benefits-benefitType">
                    <Translate contentKey="accounTalkApp.benefits.benefitType">Benefit Type</Translate>
                  </Label>
                  <AvInput
                    id="benefits-benefitType"
                    type="select"
                    className="form-control"
                    name="benefitType"
                    value={(!isNew && benefitsEntity.benefitType) || 'UNSPECIFIED'}
                  >
                    <option value="UNSPECIFIED">{translate('accounTalkApp.BenefitType.UNSPECIFIED')}</option>
                    <option value="COMPANY_VAN">{translate('accounTalkApp.BenefitType.COMPANY_VAN')}</option>
                    <option value="FUEL_VAN">{translate('accounTalkApp.BenefitType.FUEL_VAN')}</option>
                    <option value="PRIVATE_MEDICAL_DENTAL_INSURANCE">
                      {translate('accounTalkApp.BenefitType.PRIVATE_MEDICAL_DENTAL_INSURANCE')}
                    </option>
                    <option value="COMPANY_CAR">{translate('accounTalkApp.BenefitType.COMPANY_CAR')}</option>
                    <option value="FUEL_CAR">{translate('accounTalkApp.BenefitType.FUEL_CAR')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="benefits-description">
                    <Translate contentKey="accounTalkApp.benefits.description">Description</Translate>
                  </Label>
                  <AvField id="benefits-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="valueLabel" for="benefits-value">
                    <Translate contentKey="accounTalkApp.benefits.value">Value</Translate>
                  </Label>
                  <AvField id="benefits-value" type="string" className="form-control" name="value" />
                </AvGroup>
                <AvGroup>
                  <Label for="benefits-employmentDetails">
                    <Translate contentKey="accounTalkApp.benefits.employmentDetails">Employment Details</Translate>
                  </Label>
                  <AvInput id="benefits-employmentDetails" type="select" className="form-control" name="employmentDetails.id">
                    <option value="" key="0" />
                    {employmentDetails
                      ? employmentDetails.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/benefits" replace color="info">
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
  employmentDetails: storeState.employmentDetails.entities,
  benefitsEntity: storeState.benefits.entity,
  loading: storeState.benefits.loading,
  updating: storeState.benefits.updating,
  updateSuccess: storeState.benefits.updateSuccess
});

const mapDispatchToProps = {
  getEmploymentDetails,
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
)(BenefitsUpdate);
