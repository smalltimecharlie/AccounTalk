import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ClientDetail extends React.Component<IClientDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { clientEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.client.detail.title">Client</Translate> [<b>{clientEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">
                <Translate contentKey="accounTalkApp.client.title">Title</Translate>
              </span>
              <UncontrolledTooltip target="title">
                <Translate contentKey="accounTalkApp.client.help.title" />
              </UncontrolledTooltip>
            </dt>
            <dd>{clientEntity.title}</dd>
            <dt>
              <span id="forename">
                <Translate contentKey="accounTalkApp.client.forename">Forename</Translate>
              </span>
            </dt>
            <dd>{clientEntity.forename}</dd>
            <dt>
              <span id="surname">
                <Translate contentKey="accounTalkApp.client.surname">Surname</Translate>
              </span>
            </dt>
            <dd>{clientEntity.surname}</dd>
            <dt>
              <span id="dateOfBirth">
                <Translate contentKey="accounTalkApp.client.dateOfBirth">Date Of Birth</Translate>
              </span>
            </dt>
            <dd>{clientEntity.dateOfBirth}</dd>
            <dt>
              <span id="phoneNumber">
                <Translate contentKey="accounTalkApp.client.phoneNumber">Phone Number</Translate>
              </span>
            </dt>
            <dd>{clientEntity.phoneNumber}</dd>
            <dt>
              <span id="email">
                <Translate contentKey="accounTalkApp.client.email">Email</Translate>
              </span>
            </dt>
            <dd>{clientEntity.email}</dd>
            <dt>
              <span id="selfAssesmentUtrNo">
                <Translate contentKey="accounTalkApp.client.selfAssesmentUtrNo">Self Assesment Utr No</Translate>
              </span>
            </dt>
            <dd>{clientEntity.selfAssesmentUtrNo}</dd>
            <dt>
              <span id="nationality">
                <Translate contentKey="accounTalkApp.client.nationality">Nationality</Translate>
              </span>
            </dt>
            <dd>{clientEntity.nationality}</dd>
            <dt>
              <span id="gender">
                <Translate contentKey="accounTalkApp.client.gender">Gender</Translate>
              </span>
            </dt>
            <dd>{clientEntity.gender}</dd>
            <dt>
              <span id="studentLoan">
                <Translate contentKey="accounTalkApp.client.studentLoan">Student Loan</Translate>
              </span>
            </dt>
            <dd>{clientEntity.studentLoan ? 'true' : 'false'}</dd>
            <dt>
              <span id="childBenefit">
                <Translate contentKey="accounTalkApp.client.childBenefit">Child Benefit</Translate>
              </span>
            </dt>
            <dd>{clientEntity.childBenefit ? 'true' : 'false'}</dd>
            <dt>
              <span id="spouse">
                <Translate contentKey="accounTalkApp.client.spouse">Spouse</Translate>
              </span>
            </dt>
            <dd>{clientEntity.spouse ? 'true' : 'false'}</dd>
            <dt>
              <span id="findOutAboutUs">
                <Translate contentKey="accounTalkApp.client.findOutAboutUs">Find Out About Us</Translate>
              </span>
            </dt>
            <dd>{clientEntity.findOutAboutUs}</dd>
            <dt>
              <span id="additionalInformation">
                <Translate contentKey="accounTalkApp.client.additionalInformation">Additional Information</Translate>
              </span>
            </dt>
            <dd>{clientEntity.additionalInformation}</dd>
          </dl>
          <Button tag={Link} to="/entity/client" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/client/${clientEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientDetail);
