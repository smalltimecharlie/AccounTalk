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
import { getEntity, updateEntity, createEntity, reset } from './emp-pension-contributions.reducer';
import { IEmpPensionContributions } from 'app/shared/model/emp-pension-contributions.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmpPensionContributionsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmpPensionContributionsUpdateState {
  isNew: boolean;
  employmentDetailsId: string;
}

export class EmpPensionContributionsUpdate extends React.Component<
  IEmpPensionContributionsUpdateProps,
  IEmpPensionContributionsUpdateState
> {
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
      const { empPensionContributionsEntity } = this.props;
      const entity = {
        ...empPensionContributionsEntity,
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
    this.props.history.push('/entity/emp-pension-contributions');
  };

  render() {
    const { empPensionContributionsEntity, employmentDetails, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.empPensionContributions.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.empPensionContributions.home.createOrEditLabel">
                Create or edit a EmpPensionContributions
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : empPensionContributionsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="emp-pension-contributions-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="emp-pension-contributions-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="amountPaidLabel" for="emp-pension-contributions-amountPaid">
                    <Translate contentKey="accounTalkApp.empPensionContributions.amountPaid">Amount Paid</Translate>
                  </Label>
                  <AvField id="emp-pension-contributions-amountPaid" type="string" className="form-control" name="amountPaid" />
                </AvGroup>
                <AvGroup>
                  <Label for="emp-pension-contributions-employmentDetails">
                    <Translate contentKey="accounTalkApp.empPensionContributions.employmentDetails">Employment Details</Translate>
                  </Label>
                  <AvInput
                    id="emp-pension-contributions-employmentDetails"
                    type="select"
                    className="form-control"
                    name="employmentDetails.id"
                  >
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
                <Button tag={Link} id="cancel-save" to="/entity/emp-pension-contributions" replace color="info">
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
  empPensionContributionsEntity: storeState.empPensionContributions.entity,
  loading: storeState.empPensionContributions.loading,
  updating: storeState.empPensionContributions.updating,
  updateSuccess: storeState.empPensionContributions.updateSuccess
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
)(EmpPensionContributionsUpdate);
